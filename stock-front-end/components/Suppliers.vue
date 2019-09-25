<template>
  <v-container>
    <v-snackbar bottom color="primary" v-model="snackbar">New supplier {{ name }} added successfully</v-snackbar>
    <v-layout>
      <v-flex xs12 sm12 md6 offset-md3 la4 offset-la4 xl4 offset-xl4>
        <v-card>
          <v-layout>
            <v-dialog v-model="dialog" persistent max-width="600px">
              <template v-slot:activator="{ on }">
                <v-flex hidden-xs-only>
                  <v-btn fab color="primary" icon small v-on="on">
                    <v-icon>add</v-icon>
                  </v-btn>
                </v-flex>
                <v-flex hidden-sm-and-up>
                  <v-btn fab fixed bottom right color="primary" icon small v-on="on">
                    <v-icon>add</v-icon>
                  </v-btn>
                </v-flex>
              </template>
              <v-form v-model="valid" ref="supplier_creation_form" lazy-validation>
                <v-card>
                  <v-card-title>
                    <span class="headline">Add new supplier</span>
                  </v-card-title>
                  <v-card-text>
                    <v-container grid-list-md>
                      <v-layout wrap>
                        <v-flex xs12 sm6 md6>
                          <v-text-field
                            label="Name"
                            v-model="name"
                            :rules="[rules.required, rules.minLength, rules.maxNameLength]"
                          ></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md6>
                          <v-text-field label="Reference" v-model="reference"></v-text-field>
                        </v-flex>
                        <v-flex xs12>
                          <v-text-field
                            label="Address line 1"
                            v-model="address1"
                            :rules="[rules.required, rules.maxLength]"
                          ></v-text-field>
                        </v-flex>
                        <v-flex xs12>
                          <v-text-field label="Address line 2" v-model="address2"></v-text-field>
                        </v-flex>

                        <v-flex xs12 sm6 md6>
                          <v-autocomplete
                            label="Country"
                            v-model="country"
                            :rules="[rules.required]"
                            :items="['Canada']"
                          ></v-autocomplete>
                        </v-flex>
                        <v-flex xs12 sm6 md6>
                          <v-autocomplete
                            label="Province/State"
                            v-model="state"
                            :rules="[rules.required]"
                            :items="['Quebec']"
                          ></v-autocomplete>
                        </v-flex>
                        <v-spacer></v-spacer>
                        <v-flex xs12 sm6 md6>
                          <v-autocomplete
                            label="City"
                            v-model="city"
                            :rules="[rules.required]"
                            :items="['Longueuil']"
                          ></v-autocomplete>
                        </v-flex>
                        <v-flex xs12 sm6 md6>
                          <v-autocomplete
                            label="Postal code"
                            v-model="postalcode"
                            :rules="[rules.required]"
                            :items="['J4L4A8']"
                          ></v-autocomplete>
                        </v-flex>
                        <v-flex xs12 sm6 md6>
                          <v-text-field label="Email" v-model="email" :rules="[rules.emailRule]"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md6>
                          <v-text-field
                            label="Phone"
                            mask="phone"
                            v-model="phone"
                            :rules="[rules.required]"
                          ></v-text-field>
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
                      @click="addNewSupplier()"
                    >Save</v-btn>
                  </v-card-actions>
                </v-card>
              </v-form>
            </v-dialog>

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
                    <v-icon color="primary">phone</v-icon>
                  </v-list-tile-avatar>
                  <v-list-tile-avatar>
                    <v-icon color="primary">email</v-icon>
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
import { getSuppliers, postSupplier } from '~/api/suppliers'
export default {
  data() {
    return {
      suppliers: [],
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
      postalcode: null,
      snackbar: false,
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
    addNewSupplier: function() {
      if (this.$refs.supplier_creation_form.validate()) {
        let payload = {
          name: this.name,
          reference: this.reference,
          address: this.address1 + (this.address2 ? ', ' + this.address2 : ''),
          phone: this.phone,
          email: this.email
        }

        postSupplier(payload)
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
    getSuppliers().then(response => {
      this.loading = false
      this.suppliers = response.data
    })
  }
}
</script>