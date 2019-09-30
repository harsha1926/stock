<template>
  <div>
    <v-dialog :value="dialog" persistent max-width="600px">
      <v-form v-model="valid" ref="form">
        <v-card>
          <v-card-title>
            <span class="headline" justify-center>{{ dialogHeader }}</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>
                <v-flex xs12 sm6 md6>
                  <v-text-field
                    :label="$t('app.actions.name')"
                    v-model="name"
                    :rules="[rules.required, rules.minNameLength, rules.maxNameLength]"
                    counter="50"
                  ></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md6>
                  <v-text-field :label="$t('app.actions.reference')" v-model="reference"></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field
                    :label="$t('app.actions.address_1')"
                    v-model="address1"
                    :rules="[rules.required, rules.maxAddressLength]"
                    counter="50"
                  ></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field :label="$t('app.actions.address_2')" v-model="address2"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md6>
                  <v-autocomplete
                    :label="$t('app.actions.city')"
                    v-model="city"
                    :rules="[rules.required]"
                    :items="['Longueuil']"
                  ></v-autocomplete>
                </v-flex>

                <v-flex xs12 sm6 md6>
                  <v-autocomplete
                    :label="$t('app.actions.state')"
                    v-model="state"
                    :rules="[rules.required]"
                    :items="['Quebec']"
                  ></v-autocomplete>
                </v-flex>
                <v-spacer></v-spacer>
                <v-flex xs12 sm6 md6>
                  <v-autocomplete
                    :label="$t('app.actions.country')"
                    v-model="country"
                    :rules="[rules.required]"
                    :items="['Canada']"
                  ></v-autocomplete>
                </v-flex>

                <v-flex xs12 sm6 md6>
                  <v-autocomplete
                    :label="$t('app.actions.postal_code')"
                    v-model="postalCode"
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
                    mask="phone"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-flex>
              </v-layout>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary darken-1" flat @click="dialogClosed()">Close</v-btn>
            <v-btn
              color="primary darken-1"
              flat
              :disabled="!valid"
              @click="submit"
            >{{ submitLabel }}</v-btn>
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
    customer: Object,
    submitLabel: String,
    submitFunction: Function
  },
  data() {
    return {
      valid: false,
      name: null,
      reference: null,
      address1: null,
      address2: null,
      country: null,
      state: null,
      city: null,
      postalCode: null,
      email: null,
      phone: null,

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
    dialogClosed: function() {
      this.$emit('dialog-closed')
    },
    submit: function() {
      if (this.$refs.form.validate()) {
        let payload = {
          id: this.id,
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
        this.submitFunction(payload)
      }
    }
  },

  watch: {
    customer: function(newVal) {
      if (newVal) {
        this.id = newVal.id
        this.name = newVal.name
        this.reference = newVal.reference
        this.address1 = newVal.address1
        this.address2 = newVal.address2
        this.phone = newVal.phone
        this.email = newVal.email
        this.country = newVal.country
        this.state = newVal.state
        this.city = newVal.city
        this.postalCode = newVal.postalCode
      } else {
        this.id = null
        this.name = null
        this.reference = null
        this.address1 = null
        this.address2 = null
        this.phone = null
        this.email = null
        this.country = null
        this.state = null
        this.city = null
        this.postalCode = null
      }
    }
  }
}
</script>