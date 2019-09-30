<template>
  <div>
    <v-dialog :value="dialog" persistent max-width="600px">
      <v-form v-model="valid" ref="form">
        <v-card>
          <v-card-title>
            <span class="headline">{{ dialogHeader}}</span>
          </v-card-title>
          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>
                <v-flex xs12>
                  <v-text-field label="Name" v-model="name" :rules="[rules.required]"></v-text-field>
                </v-flex>
                <v-flex xs12>
                  <v-text-field label="Category" v-model="category" :rules="[rules.required]"></v-text-field>
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
              @click="submit()"
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
    product: Object,
    submitLabel: String,
    submitFunction: Function
  },
  data() {
    return {
      valid: false,
      name: null,
      category: null,
      rules: {
        required: v => !!v || 'Required field'
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
          category: this.category
        }
        this.submitFunction(payload)
      }
    }
  },
  watch: {
    product: function(newVal) {
      if (newVal) {
        this.id = newVal.id
        this.name = newVal.name
        this.category = newVal.category
      } else {
        this.id = null
        this.name = null
        this.category = null
      }
    }
  }
}
</script>