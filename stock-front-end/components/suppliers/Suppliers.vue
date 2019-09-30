<template>
  <v-container fluid ma-0 pa-0>
    <v-snackbar bottom color="primary" :value="snackbar">{{ successMessage }}</v-snackbar>
    <v-flex xs12 sm12 md6 offset-md3 la4 offset-la4 xl4 offset-xl4>
      <v-card>
        <v-layout pt-3>
          <supplier-form
            :dialog="showDialog"
            :dialogHeader="dialogHeader"
            @dialog-closed="showDialog = false"
            :submitLabel="submitLabel"
            :submitFunction="submitFunction"
            :supplier="selectedSupplier"
          />

          <v-flex hidden-xs-only>
            <v-btn fab color="primary" @click="addSupplier()" icon small>
              <v-icon>add</v-icon>
            </v-btn>
          </v-flex>
          <v-flex hidden-sm-and-up>
            <v-btn fab fixed bottom right color="primary" @click="addSupplier()" icon small>
              <v-icon>add</v-icon>
            </v-btn>
          </v-flex>
          <v-layout>
            <v-card-title class="title">{{ $t('app.actions.suppliers') }}</v-card-title>
          </v-layout>
        </v-layout>
        <v-card-text>
          <v-layout justify-center>
            <span v-if="loading">
              <v-progress-circular indeterminate color="secondary"></v-progress-circular>
              <span>Please wait..</span>
            </span>

            <v-list three-line>
              <v-list-tile v-for="supplier in suppliers" :key="supplier.id">
                <v-list-tile-content>
                  <v-list-tile-title>
                    {{ supplier.name }}
                    <span
                      class="caption font-weight-thin"
                    >{{ supplier.reference }}</span>
                  </v-list-tile-title>
                  <v-list-tile-sub-title>{{ supplier.address1 }}</v-list-tile-sub-title>
                  <v-list-tile-sub-title>{{ supplier.address2 }}</v-list-tile-sub-title>
                </v-list-tile-content>

                <v-list-tile-avatar>
                  <v-icon color="primary" @click="callPhone(supplier.phone)">phone</v-icon>
                </v-list-tile-avatar>

                <v-list-tile-avatar>
                  <v-menu bottom left>
                    <template v-slot:activator="{ on }">
                      <v-icon color="primary" v-on="on">more_vert</v-icon>
                    </template>
                    <v-list>
                      <v-list-tile key="email">
                        <v-list-tile-avatar>
                          <v-icon color="primary" @click="sendEmail(supplier.email)">email</v-icon>
                        </v-list-tile-avatar>
                      </v-list-tile>
                      <v-list-tile key="edit">
                        <v-list-tile-avatar>
                          <v-icon color="primary" @click="updateSupplier(supplier)">edit</v-icon>
                        </v-list-tile-avatar>
                      </v-list-tile>
                      <v-list-tile key="delete">
                        <v-list-tile-avatar>
                          <v-icon color="primary" @click="deleteThisSupplier(supplier)">delete</v-icon>
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
    <v-dialog persistent v-model="deleteWarningDialog" max-width="400">
      <v-card>
        <v-card-title class="headline">Do you really want to get rid of him?</v-card-title>
        <v-card-actions>
          <v-btn flat color="primary" @click="deleteWarningDialog = false">Cancel</v-btn>
          <v-btn flat @click="deleteSelectedSupplier()">Sure</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>
<script>
import {
  getSuppliers,
  postSupplier,
  deleteSupplier,
  putSupplier
} from '~/api/suppliers'
import SupplierForm from './SupplierForm'

export default {
  components: {
    SupplierForm
  },
  data() {
    return {
      deleteWarningDialog: false,
      showDialog: false,
      dialogHeader: null,
      submitLabel: null,
      submitFunction: null,
      snackbar: false,
      successMessage: null,
      suppliers: [],
      loading: true,
      update: false,
      on: false,
      selectedSupplier: null
    }
  },
  methods: {
    deleteThisSupplier(supplier) {
      this.selectedSupplier = supplier
      this.deleteWarningDialog = true
    },
    addSupplier() {
      this.selectedSupplier = null
      this.dialogHeader = 'Add new Supplier'
      this.showDialog = true
      this.submitLabel = 'Add'
      this.submitFunction = this.addNewSupplier
    },
    updateSupplier(supplier) {
      this.selectedSupplier = supplier
      this.dialogHeader = 'Update Supplier'
      this.showDialog = true
      this.submitLabel = 'Save'
      this.submitFunction = this.updateSelectedSupplier
    },
    callPhone: function(phoneNumber) {
      window.open('tel:' + phoneNumber)
    },
    sendEmail: function(email) {
      window.open('mailto:' + email)
    },
    addNewSupplier: function(payload) {
      postSupplier(payload)
        .then(response => {
          if (response.data) {
            this.successMessage =
              'New supplier ' + payload.name + ' added successfully!'
            this.snackbar = true
            this.showDialog = false
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    deleteSelectedSupplier: function() {
      deleteSupplier(this.selectedSupplier.id)
        .then(response => {
          if (response.data) {
            this.successMessage =
              'Supplier ' +
              this.selectedSupplier.name +
              ' deleted successfully!'
            this.snackbar = true
            this.deleteWarningDialog = false
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    updateSelectedSupplier: function(payload) {
      putSupplier(payload)
        .then(response => {
          if (response.data) {
            this.successMessage =
              'Supplier ' + payload.name + ' updated successfully!'
            this.snackbar = true
            this.showDialog = false
          }
        })
        .catch(error => {
          console.error(error)
        })
    }
  },
  mounted: function() {
    getSuppliers().then(response => {
      this.loading = false
      this.suppliers = response.data
    })
  }
}
</script>