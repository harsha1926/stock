<template>
  <v-container fluid>
    <v-snackbar bottom color="primary" v-model="snackbar">New product {{ name }} added successfully</v-snackbar>
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
              <v-form v-model="valid" ref="form">
                <v-card>
                  <v-card-title>
                    <span class="headline">Add new product</span>
                  </v-card-title>
                  <v-card-text>
                    <v-container grid-list-md>
                      <v-layout wrap>
                        <v-flex xs12>
                          <v-text-field label="Name" v-model="name" required></v-text-field>
                        </v-flex>
                        <v-flex xs12>
                          <v-text-field label="Category" v-model="category" required></v-text-field>
                        </v-flex>
                      </v-layout>
                    </v-container>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="primary darken-1" flat @click="dialog = false">Close</v-btn>
                    <v-btn
                      color="primary darken-1"
                      flat
                      :disabled="!valid"
                      @click="addNewProduct()"
                    >Save</v-btn>
                  </v-card-actions>
                </v-card>
              </v-form>
            </v-dialog>

            <v-layout>
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
                <v-list-tile v-for="product in products" :key="product.id">
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
import { getProducts, postProduct } from '~/api/products'
export default {
  data() {
    return {
      products: [],
      loading: true,
      dialog: false,
      valid: false,
      name: null,
      category: null,
      snackbar: false
    }
  },
  methods: {
    addNewProduct: function() {
      if (this.$refs.form.validate()) {
        let payload = {
          name: this.name,
          category: this.category
        }

        postProduct(payload)
          .then(response => {
            if (response.data) {
              this.snackbar = true
              this.dialog = false
            }
          })
          .catch(error => {
            console.error(error)
          })
      }
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
