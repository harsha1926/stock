<template>
  <v-container fluid>
    <v-layout>
      <v-flex xs12 sm12 md6 offset-md3 la4 offset-la4 xl4 offset-xl4>
        <v-card>
          <v-layout>
            <v-dialog v-model="dialog" persistent max-width="600px">
              <template v-slot:activator="{ on }">
                <v-btn fab color="primary" icon small v-on="on">
                  <v-icon>add</v-icon>
                </v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="headline">Create New Product</span>
                </v-card-title>
                <v-card-text>
                  <v-container grid-list-md>
                    <v-layout wrap>
                      <v-flex xs12>
                        <v-text-field label="Product Name*" required></v-text-field>
                      </v-flex>
                      <v-flex xs12>
                        <v-text-field label="Category*" required></v-text-field>
                      </v-flex>
                    </v-layout>
                  </v-container>
                  <small>*indicates required field</small>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" flat @click="dialog = false">Close</v-btn>
                  <v-btn color="blue darken-1" flat @click="dialog = false">Save</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>

            <v-layout justify-center>
              <v-card-title class="title">{{ $t('app.actions.products') }}</v-card-title>
            </v-layout>
          </v-layout>
          <v-card-text>
            <v-layout justify-center>
              <span v-if="loading">
                <v-progress-circular indeterminate color="secondary"></v-progress-circular>
                <span>Please wait..</span>
              </span>
              <v-list>
                <v-list-tile v-for="product in products" :key="product.name">
                  <v-list-tile-content>
                    <v-list-tile-title>
                      {{ product.name }}
                      <span class="caption">{{ product.category }}</span>
                    </v-list-tile-title>
                  </v-list-tile-content>
                </v-list-tile>
              </v-list>
            </v-layout>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>
<script>
import { getProducts } from '~/api/products'
export default {
  data() {
    return {
      products: [],
      loading: true,
      dialog: false
    }
  },
  mounted: function() {
    getProducts().then(response => {
      this.loading = false
      this.products = response.data
    })
  }
}
</script>
