<template>
  <v-container fluid>
    <v-snackbar bottom color="primary" v-model="snackbar">New customer {{ name }} added successfully</v-snackbar>
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
                    <span class="headline">{{$t('app.actions.add_new_customer')}}</span>
                  </v-card-title>
                  <v-card-text>
                    <v-container grid-list-md>
                      <v-layout wrap>
                        <v-flex xs12 sm6 md6>
                          <v-text-field
                            :label="$t('app.actions.name')"
                            v-model="name"
                            :rules="[rules.required, rules.minLength, rules.maxNameLength]"
                          ></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md6>
                          <v-text-field :label="$t('app.actions.reference')" v-model="reference"></v-text-field>
                        </v-flex>
                        <v-flex xs12>
                          <v-text-field
                            :label="$t('app.actions.address_1')"
                            v-model="address1"
                            :rules="[rules.required, rules.maxLength]"
                          ></v-text-field>
                        </v-flex>
                        <v-flex xs12>
                          <v-text-field :label="$t('app.actions.address_2')" v-model="address2"></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md6>
                          <v-autocomplete
                            :label="$t('app.actions.country')"
                            :rules="[rules.required]"
                            :items="['Canada']"
                          ></v-autocomplete>
                        </v-flex>
                        <v-flex xs12 sm6 md6>
                          <v-autocomplete
                            :label="$t('app.actions.state')"
                            :rules="[rules.required]"
                            :items="['Quebec']"
                          ></v-autocomplete>
                        </v-flex>
                        <v-spacer></v-spacer>
                        <v-flex xs12 sm6 md6>
                          <v-autocomplete
                            :label="$t('app.actions.city')"
                            :rules="[rules.required]"
                            :items="['Longueuil']"
                          ></v-autocomplete>
                        </v-flex>
                        <v-flex xs12 sm6 md6>
                          <v-autocomplete
                            :label="$t('app.actions.postal_code')"
                            :rules="[rules.required]"
                            :items="['J4L4A8']"
                          ></v-autocomplete>
                        </v-flex>
                        <v-flex xs12 sm6 md6>
                          <v-text-field
                            :label="$t('app.actions.email')"
                            v-model="email"
                            :rules="[rules.emailRule]"
                          ></v-text-field>
                        </v-flex>
                        <v-flex xs12 sm6 md6>
                          <v-text-field
                            :label=" $t('app.actions.phone')"
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
                      @click="addNewCustomer()"
                    >Save</v-btn>
                  </v-card-actions>
                </v-card>
              </v-form>
            </v-dialog>
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
import { getCustomers, postCustomer } from '~/api/customers'
export default {
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
      postalcode: null,
      snackbar: false,
      rules: {
        required: v => !!v || 'Required field',
        minLength: v =>
          (v && v.length > 2) || 'Name should be minimum of 3 characters',
        maxNameLength: v =>
          (v && v.length <= 50) || 'Name should be maximun of 50 characters',
        maxAddressLength: v =>
          (v && v.length <= 50) || 'Name should be maximun of 50 characters',
        emailRule: v => /.+@.+/.test(v) || 'E-mail must be valid'
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
          email: this.email
        }

        postCustomer(payload)
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
    getCustomers().then(response => {
      this.loading = false
      this.customers = response.data
    })
  }
}
</script>
