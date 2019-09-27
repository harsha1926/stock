<template>
  <v-container fluid ma-0 pa-0>
    <v-flex xs12 sm12 md6 offset-md3 la4 offset-la4 xl4 offset-xl4>
      <v-card>
        <v-layout pt-3>
          <supplier-form
            :dialog="showCreateDialog"
            @dialog-closed="showCreateDialog = false"
            submitLabel="Add"
            :submitFunction="addNewSupplier"
            :snackbar="showSnackbar"
            :successMessage="successMessage"
          />

          <v-flex hidden-xs-only>
            <v-btn fab color="primary" @click="showCreateDialog = true" icon small>
              <v-icon>add</v-icon>
            </v-btn>
          </v-flex>
          <v-flex hidden-sm-and-up>
            <v-btn
              fab
              fixed
              bottom
              right
              color="primary"
              @click="showCreateDialog = true"
              icon
              small
            >
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
                  <v-list-tile-sub-title>{{ supplier.address }}</v-list-tile-sub-title>
                </v-list-tile-content>

                <v-list-tile-avatar>
                  <v-icon color="primary" v-on="on" @click="callPhone(supplier.phone)">phone</v-icon>
                </v-list-tile-avatar>

                <v-list-tile-avatar>
                  <v-menu bottom left>
                    <template v-slot:activator="{ on }">
                      <v-icon color="primary" v-on="on">more_vert</v-icon>
                    </template>
                    <v-list>
                      <v-list-tile key="email">
                        <v-list-tile-avatar>
                          <v-icon color="primary" v-on="on" @click="sendEmail(supplier.email)">email</v-icon>
                        </v-list-tile-avatar>
                      </v-list-tile>
                      <v-list-tile key="edit">
                        <v-list-tile-avatar>
                          <v-icon
                            color="primary"
                            v-on="on"
                            @click="showUpdateDialog = true; selectedSupplier = supplier"
                          >edit</v-icon>
                        </v-list-tile-avatar>
                      </v-list-tile>
                      <v-list-tile key="delete">
                        <v-list-tile-avatar>
                          <v-icon
                            color="primary"
                            v-on="on"
                            @click="deleteSelectedSupplier(supplier)"
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
    <supplier-form
      :dialog="showUpdateDialog"
      @dialog-closed="showUpdateDialog = false"
      submitLabel="Save"
      :submitFunction="updateSupplier"
      :snackbar="showSnackbar"
      :successMessage="successMessage"
      :supplier="selectedSupplier"
    />
    <v-snackbar bottom color="primary" v-model="showSnackbar">{{ successMessage }}</v-snackbar>
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
      showCreateDialog: false,
      showSnackbar: false,
      suppliers: [],
      loading: true,
      update: false,
      on: false,
      successMessage: null,
      showUpdateDialog: false,
      selectedSupplier: null
    }
  },
  methods: {
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
            this.showSnackbar = true
            this.showCreateDialog = false
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    deleteSelectedSupplier: function(supplier) {
      deleteSupplier(supplier.id)
        .then(response => {
          if (response.data) {
            this.successMessage =
              'Supplier ' + supplier.name + ' deleted successfully!'
            this.showSnackbar = true
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    updateSupplier: function(payload) {
      putSupplier(payload)
        .then(response => {
          if (response.data) {
            this.successMessage =
              'Supplier ' + payload.name + ' updated successfully!'
            this.showSnackbar = true
            this.showUpdateDialog = false
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