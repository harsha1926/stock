<template>
  <v-content>
    <supplier-transactions />
  </v-content>
</template>

<script>
import SupplierTransactions from '~/components/SupplierTransactions'
export default {
  components: {
    SupplierTransactions
  },
  layout: 'default'
}
</script>
