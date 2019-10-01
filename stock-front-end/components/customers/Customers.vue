<template>
  <v-container fluid ma-0 pa-0>
    <v-snackbar bottom color="primary" v-model="snackbar">{{ snackbarMessage }}</v-snackbar>
    <v-flex xs12 sm12 md6 offset-md3 la4 offset-la4 xl4 offset-xl4>
      <v-card>
        <v-layout pt-3>
          <customer-form
            :dialog="showDialog"
            @dialog-closed="showDialog=false"
            :dialogHeader="dialogHeader"
            :customer="selectedCustomer"
            :submitLabel="submitLabel"
            :submitFunction="submitFunction"
          />
          <v-flex hidden-xs-only>
            <v-btn fab color="primary" icon small @click="addCustomer()">
              <v-icon>add</v-icon>
            </v-btn>
          </v-flex>
          <v-flex hidden-sm-and-up>
            <v-btn fab color="primary" bottom right fixed icon small @click="addCustomer()">
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
                  <v-list-tile-sub-title>{{ customer.address1 }}</v-list-tile-sub-title>
                  <v-list-tile-sub-title>{{ customer.address2 }}</v-list-tile-sub-title>
                </v-list-tile-content>

                <v-list-tile-avatar>
                  <v-icon color="primary" @click="callPhone(customer.phone)">phone</v-icon>
                </v-list-tile-avatar>
                <v-list-tile-avatar>
                  <v-menu bottom left>
                    <template v-slot:activator="{ on }">
                      <v-icon color="primary" v-on="on">more_vert</v-icon>
                    </template>
                    <v-list>
                      <v-list-tile>
                        <v-list-tile-avatar>
                          <v-icon color="primary" @click="sendEmail(customer.email)">email</v-icon>
                        </v-list-tile-avatar>
                      </v-list-tile>
                      <v-list-tile>
                        <v-list-tile-avatar>
                          <v-icon color="primary" @click="updateCustomer(customer)">edit</v-icon>
                        </v-list-tile-avatar>
                      </v-list-tile>
                      <v-list-tile>
                        <v-list-tile-avatar>
                          <v-icon
                            color="primary"
                            @click="deleteThisCustomer(customer)"
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
    <v-dialog v-model="deleteWarningDialog" persistent max-width="400">
      <v-card>
        <v-card-title>Are you sure you want to get rid of him?</v-card-title>
        <v-card-actions>
          <v-btn color="primary" @click="deleteWarningDialog=false">Cancel</v-btn>
          <v-btn @click="deleteSelectedCustomer()">Sure</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>
<script>
import {
  getCustomers,
  postCustomer,
  deleteCustomer,
  putCustomer
} from '~/api/customers'
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
      showDialog: false,
      dialogHeader: null,
      submitLabel: null,
      selectedCustomer: null,
      submitFunction: null,
      deleteWarningDialog: false,
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
    addNewCustomer: function(payload) {
      postCustomer(payload)
        .then(response => {
          if (response.data) {
            this.snackbarMessage =
              'New customer ' + payload.name + ' added successfully!'
            this.snackbar = true
            this.showDialog = false
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    deleteThisCustomer: function(customer) {
      this.selectedCustomer = customer
      this.deleteWarningDialog = true
    },
    deleteSelectedCustomer: function() {
      deleteCustomer(this.selectedCustomer.id)
        .then(response => {
          if (response.data) {
            this.snackbarMessage =
              'Customer ' +
              this.selectedCustomer.name +
              ' deleted successfully!'
            this.snackbar = true
            this.deleteWarningDialog = false
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    updateSelectedCustomer: function(payload) {
      putCustomer(payload)
        .then(response => {
          if (response.data) {
            this.snackbarMessage =
              'Customer ' + payload.name + ' updated successfully!'
            this.snackbar = true
            this.showDialog = false
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    addCustomer: function() {
      this.showDialog = true
      this.dialogHeader = this.$t('app.actions.form.add_new_customer')
      this.selectedCustomer = null
      this.submitLabel = this.$t('app.actions.form.save')
      this.submitFunction = this.addNewCustomer
    },
    updateCustomer: function(customer) {
      this.showDialog = true
      this.dialogHeader = this.$t('app.actions.form.update_customer')
      this.selectedCustomer = customer
      this.submitLabel = this.$t('app.actions.form.update')
      this.submitFunction = this.updateSelectedCustomer
    },
    callPhone: function(phoneNumber) {
      window.open('tel:' + phoneNumber)
    },
    sendEmail: function(email) {
      window.open('mailto:' + email)
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
