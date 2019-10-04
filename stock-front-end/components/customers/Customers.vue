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
            <v-btn fab color="primary" icon small @click="openAddCustomerDialog()">
              <v-icon>add</v-icon>
            </v-btn>
          </v-flex>
          <v-flex hidden-sm-and-up>
            <v-btn
              fab
              color="primary"
              bottom
              right
              fixed
              icon
              small
              @click="openAddCustomerDialog()"
            >
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
                          <v-icon color="primary" @click="openEditCustomerDialog(customer)">edit</v-icon>
                        </v-list-tile-avatar>
                      </v-list-tile>
                      <v-list-tile>
                        <v-list-tile-avatar>
                          <v-icon
                            color="primary"
                            @click="openDeleteCustomerWarningDialog(customer)"
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
import CustomerForm from './CustomerForm'
import { mapGetters, mapActions } from 'vuex'
export default {
  components: {
    CustomerForm
  },
  data() {
    return {
      loading: true,
      snackbar: false,
      on: false,
      snackbarMessage: null,
      showDialog: false,
      dialogHeader: null,
      submitLabel: null,
      selectedCustomer: null,
      submitFunction: null,
      deleteWarningDialog: false
    }
  },
  computed: {
    ...mapGetters({
      customers: 'customers/customers'
    })
  },
  methods: {
    ...mapActions({
      getCustomers: 'customers/getCustomers',
      createCustomer: 'customers/createCustomer',
      updateCustomer: 'customers/updateCustomer',
      removeCustomer: 'customers/removeCustomer'
    }),
    addNewCustomer: function(payload) {
      this.createCustomer(payload)
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
    openDeleteCustomerWarningDialog: function(customer) {
      this.selectedCustomer = customer
      this.deleteWarningDialog = true
    },
    deleteSelectedCustomer: function() {
      this.removeCustomer(this.selectedCustomer.id)
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
      this.updateCustomer(payload)
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
    openAddCustomerDialog: function() {
      this.showDialog = true
      this.dialogHeader = this.$t('app.actions.form.add_new_customer')
      this.selectedCustomer = null
      this.submitLabel = this.$t('app.actions.form.save')
      this.submitFunction = this.addNewCustomer
    },
    openEditCustomerDialog: function(customer) {
      this.showDialog = true
      this.dialogHeader = this.$t('app.actions.form.update_customer')
      this.selectedCustomer = customer
      this.submitLabel = this.$t('app.actions.form.save')
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
    this.getCustomers().then(response => {
      this.loading = false
    })
  }
}
</script>
