<template>
  <div>
    <v-dialog :value="dialog" persistent max-width="600px">
      <v-form v-model="valid" ref="stockParcel_creation_form" lazy-validation>
        <v-card>
          <v-card-title>
            <span class="headline">{{ dialogHeader }}</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>
                <v-flex xs12 sm6 md6>
                  <v-text-field label="Supplier Name" v-model="supplierName"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md6>
                  <v-text-field label="Bill Number" v-model="billNumber"></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field label="Total Amount" v-model="totalAmount"></v-text-field>
                </v-flex>
              </v-layout>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn flat @click="dialogClosed()">Close</v-btn>
            <v-btn color="primary" flat :disabled="!valid" @click="submit()">{{ submitLabel }}</v-btn>
          </v-card-actions>
        </v-card>
      </v-form>
    </v-dialog>
  </div>
</template>

<script>
export default {
  props: {
    dialog: Boolean,
    dialogHeader: String,
    submitLabel: String,
    submitFunction: Function,
    stockParcel: Object
  },
  data() {
    return {
      id: null,
      supplierName: null,
      billNumber: null,
      totalAmount: null,
      valid: false
    }
  },
  methods: {
    dialogClosed: function() {
      this.$emit('dialog-closed')
    },
    submit: function() {
      if (this.$refs.stockParcel_creation_form.validate()) {
        let payload = {
          id: this.id,
          supplierName: this.supplierName,
          billNumber: this.billNumber,
          totalAmount: this.totalAmount
        }
        this.submitFunction(payload)
      }
    }
  },
  watch: {
    stockParcel: function(newVal) {
      if (newVal) {
        this.id = newVal.id
        this.supplierName = newVal.supplierName
        this.billNumber = newVal.billNumber
        this.totalAmount = newVal.totalAmount
      } else {
        this.id = null
        this.supplierName = null
        this.billNumber = null
        this.totalAmount = null
      }
    }
  }
}
</script>