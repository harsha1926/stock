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
                  <span class="headline">Create New Customer</span>
                </v-card-title>
                <v-card-text>
                  <v-container grid-list-md>
                    <v-layout wrap>
                      <v-flex xs12 sm6 md4>
                        <v-text-field label="Customer Name*" required></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md4>
                        <v-text-field label="Reference"></v-text-field>
                      </v-flex>
                      <v-flex xs12>
                        <v-text-field label="Address line 1*" required></v-text-field>
                      </v-flex>
                      <v-flex xs12>
                        <v-text-field label="Address line 2"></v-text-field>
                      </v-flex>
                      <v-flex xs12>
                        <v-text-field label="Email"></v-text-field>
                      </v-flex>
                      <v-flex xs12>
                        <v-text-field label="Phone Number*" required></v-text-field>
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
              <v-card-title class="title">{{ $t('app.actions.customers') }}</v-card-title>
            </v-layout>
          </v-layout>
          <v-card-text>
            <v-layout justify-center>
              <span v-if="loading">
                <v-progress-circular color="secondary" indeterminate></v-progress-circular>
                <span>Please wait..</span>
              </span>
              <v-list three-line>
                <v-list-tile v-for="customer in customers" :key="customer.name">
                  <v-list-tile-content>
                    <v-list-tile-title>
                      {{ customer.name }}
                      <span
                        class="caption font-weight-thin"
                      >{{ customer.reference }}</span>
                    </v-list-tile-title>
                    <v-list-tile-sub-title>{{ customer.address }}</v-list-tile-sub-title>
                  </v-list-tile-content>
                  <v-list-tile-avatar>
                    <v-icon>phone</v-icon>
                  </v-list-tile-avatar>
                  <v-list-tile-avatar>
                    <v-icon>email</v-icon>
                  </v-list-tile-avatar>
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
import { getCustomers } from '~/api/customers'
export default {
  data() {
    return {
      customers: [],
      loading: true,
      dialog: false
    }
  },
  mounted: function() {
    getCustomers().then(response => {
      this.loading = false
      this.customers = response.data
    })
  }
}
</script>
