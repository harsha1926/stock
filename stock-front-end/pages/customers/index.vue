<template>
  <v-content>
    <customers />
  </v-content>
</template>

<script>
import Customers from '~/components/customers/Customers'
export default {
  components: {
    Customers
  },
  layout: 'default'
}
</script>
