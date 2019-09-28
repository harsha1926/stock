<template>
  <v-container fluid ma-0 pa-0>
    <v-snackbar bottom color="primary" v-model="snackbar">{{ snackbarMessage }}</v-snackbar>
    <v-flex xs12 sm12 md6 offset-md3 la4 offset-la4 xl4 offset-xl4>
      <v-card>
        <v-layout pt-3>
          <customer-form :dialog="showCreateDialog" @dialog-closed="showCreateDialog=false" />
          <v-flex hidden-xs-only>
            <v-btn fab color="primary" icon small @click="showCreateDialog=true">
              <v-icon>add</v-icon>
            </v-btn>
          </v-flex>
          <v-flex hidden-sm-and-up>
            <v-btn fab color="primary" bottom right fixed icon small @click="showCreateDialog=true">
              <v-icon>add</v-icon>
            </v-btn>
          </v-flex>

          <v-layout>
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
              <v-list-tile v-for="customer in customers" :key="customer.id">
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
                  <v-icon color="primary">phone</v-icon>
                </v-list-tile-avatar>
                <v-list-tile-avatar>
                  <v-menu bottom left>
                    <template v-slot:activator="{ on }">
                      <v-icon color="primary" v-on="on">more_vert</v-icon>
                    </template>
                    <v-list>
                      <v-list-tile>
                        <v-list-tile-avatar>
                          <v-icon color="primary">email</v-icon>
                        </v-list-tile-avatar>
                      </v-list-tile>
                      <v-list-tile>
                        <v-list-tile-avatar>
                          <v-icon color="primary">edit</v-icon>
                        </v-list-tile-avatar>
                      </v-list-tile>
                      <v-list-tile>
                        <v-list-tile-avatar>
                          <v-icon
                            color="primary"
                            @click="deleteSelectedCustomer(customer)"
                            v-on="on"
                          >delete</v-icon>
                        </v-list-tile-avatar>
                      </v-list-tile>
                    </v-list>
                  </v-menu>
                </v-list-tile-avatar>
              </v-list-tile>
            </v-list>
          </v-layout>
        </v-card-text>
      </v-card>
    </v-flex>
  </v-container>
</template>
<script>
import { getCustomers, postCustomer, deleteCustomer } from '~/api/customers'
import CustomerForm from './CustomerForm'
export default {
  components: {
    CustomerForm
  },
  data() {
    return {
      customers: [],
      loading: true,
      dialog: false,
      valid: false,
      name: null,
      reference: null,
      address1: null,
      address2: null,
      phone: null,
      email: null,
      country: null,
      state: null,
      city: null,
      postalCode: null,
      snackbar: false,
      on: false,
      snackbarMessage: null,
      showCreateDialog: false,
      rules: {
        required: v => !!v || this.$t('app.actions.validations.required'),
        minNameLength: v =>
          (v && v.length > 2) ||
          this.$t('app.actions.validations.min_name_length'),
        maxNameLength: v =>
          (v && v.length <= 50) ||
          this.$t('app.actions.validations.max_name_length'),
        maxAddressLength: v =>
          (v && v.length <= 50) ||
          this.$t('app.actions.validations.max_address_length'),
        emailRule: v =>
          /.+@.+/.test(v) || this.$t('app.actions.validations.email')
      }
    }
  },
  methods: {
    addNewCustomer: function() {
      if (this.$refs.form.validate()) {
        let payload = {
          name: this.name,
          reference: this.reference,
          address: this.address1 + (this.address2 ? ', ' + this.address2 : ''),
          phone: this.phone,
          email: this.email,
          country: this.country,
          state: this.state,
          city: this.city,
          postalCode: this.postalCode
        }

        postCustomer(payload)
          .then(response => {
            if (response.data) {
              this.snackbarMessage =
                'New customer ' + this.name + ' added successfully!'
              this.snackbar = true
              this.dialog = false
            }
          })
          .catch(error => {
            console.error(error)
          })
      }
    },
    deleteSelectedCustomer: function(customer) {
      deleteCustomer(customer.id)
        .then(response => {
          if (response.data) {
            this.snackbarMessage =
              'Customer ' + customer.name + ' deleted successfully!'
            this.snackbar = true
            this.dialog = false
          }
        })
        .catch(error => {
          console.error(error)
        })
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
