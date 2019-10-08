<template>
  <v-container fluid ma-0 pa-0>
    <v-snackbar bottom color="primary" v-model="snackbar">{{ successMessage }}</v-snackbar>
    <v-flex xs12 sm12 md6 offset-md3 la4 offset-la4 xl4 offset-xl4>
      <v-card>
        <v-layout pt-3>
          <stock-parcel-form
            :dialog="showDialog"
            :dialogHeader="dialogHeader"
            @dialog-closed="showDialog = false"
            :submitLabel="submitLabel"
            :submitFunction="submitFunction"
            :stockParcel="selectedStockParcel"
          />

          <v-flex hidden-xs-only>
            <v-btn fab color="primary" @click="openAddStockParcelDialog()" icon small>
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
              @click="openAddStockParcelDialog()"
              icon
              small
            >
              <v-icon>add</v-icon>
            </v-btn>
          </v-flex>
          <v-layout>
            <v-card-title class="title">{{ $t('app.actions.stock_parcel') }}</v-card-title>
          </v-layout>
        </v-layout>
        <v-card-text>
          <v-layout justify-center>
            <span v-if="loading">
              <v-progress-circular indeterminate color="secondary"></v-progress-circular>
              <span>Please wait..</span>
            </span>

            <v-list three-line>
              <v-list-tile v-for="stockParcel in stockParcels" :key="stockParcel.id">
                <v-list-tile-content>
                  <v-list-tile-title>{{ stockParcel.supplierName }}</v-list-tile-title>
                  <v-list-tile-sub-title>{{ stockParcel.billNumber }}</v-list-tile-sub-title>
                  <v-list-tile-sub-title>{{ stockParcel.totalAmount }}</v-list-tile-sub-title>
                </v-list-tile-content>

                <v-list-tile-avatar>
                  <v-menu bottom left>
                    <template v-slot:activator="{ on }">
                      <v-icon color="primary" v-on="on">more_vert</v-icon>
                    </template>
                    <v-list>
                      <v-list-tile key="edit">
                        <v-list-tile-avatar>
                          <v-icon
                            color="primary"
                            @click="openEditStockParcelDialog(stockParcel)"
                          >edit</v-icon>
                        </v-list-tile-avatar>
                      </v-list-tile>
                      <v-list-tile key="delete">
                        <v-list-tile-avatar>
                          <v-icon
                            color="primary"
                            @click="openDeleteStockParcelWarningDialog(stockParcel)"
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
    <v-dialog persistent v-model="deleteWarningDialog" max-width="400">
      <v-card>
        <v-card-title class="headline">Do you really want to delete ?</v-card-title>
        <v-card-actions>
          <v-btn flat color="primary" @click="deleteWarningDialog = false">Cancel</v-btn>
          <v-btn flat @click="deleteSelectedStockParcel()">Sure</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>


<script>
import StockParcelForm from './StockParcelForm'
import { mapGetters, mapActions } from 'vuex'
export default {
  components: {
    StockParcelForm
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
      loading: true,
      on: false,
      selectedStockParcel: null
    }
  },
  computed: {
    ...mapGetters({
      stockParcels: 'stockParcels/stockParcels'
    })
  },
  methods: {
    ...mapActions({
      getStockParcels: 'stockParcels/getStockParcels',
      createStockParcel: 'stockParcels/createStockParcel',
      updateStockParcel: 'stockParcels/updateStockParcel',
      removeStockParcel: 'stockParcels/removeStockParcel'
    }),
    openDeleteStockParcelWarningDialog(stockParcel) {
      this.selectedStockParcel = stockParcel
      this.deleteWarningDialog = true
    },
    openAddStockParcelDialog() {
      this.selectedStockParcel = null
      this.dialogHeader = this.$t('app.actions.form.add_new_supplier')
      this.showDialog = true
      this.submitLabel = this.$t('app.actions.form.add')
      this.submitFunction = this.addNewStockParcel
    },
    openEditStockParcelDialog(stockParcel) {
      this.selectedStockParcel = stockParcel
      this.dialogHeader = this.$t('app.actions.form.update_supplier')
      this.showDialog = true
      this.submitLabel = this.$t('app.actions.form.save')
      this.submitFunction = this.updateSelectedStockParcel
    },

    addNewStockParcel: function(payload) {
      this.createStockParcel(payload)
        .then(response => {
          if (response.data) {
            this.successMessage =
              'New parcel ' + payload.supplierName + ' added successfully!'
            this.snackbar = true
            this.showDialog = false
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    deleteSelectedStockParcel: function() {
      this.removeStockParcel(this.selectedStockParcel.id)
        .then(response => {
          if (response.data) {
            this.successMessage =
              'Parcel ' +
              this.selectedStockParcel.supplierName +
              ' removed successfully!'
            this.snackbar = true
            this.deleteWarningDialog = false
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    updateSelectedStockParcel: function(payload) {
      this.updateStockParcel(payload)
        .then(response => {
          if (response.data) {
            this.successMessage =
              'Parcel ' + payload.supplierName + ' updated successfully!'
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
    this.getStockParcels().then(response => {
      this.loading = false
    })
  }
}
</script>
}
</script>