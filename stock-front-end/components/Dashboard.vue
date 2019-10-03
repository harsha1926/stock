<template>
  <v-container fluid ma-0 pa-0>
    <v-layout row justify-center>
      <v-card>
        <v-card-text>{{ suppliers }}</v-card-text>
      </v-card>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  data() {
    return {}
  },
  computed: {
    ...mapGetters({
      suppliers: 'suppliers/suppliers'
    })
  }
}
</script>
