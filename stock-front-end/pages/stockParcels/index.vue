<template>
  <v-content>
    <stock-parcels />
  </v-content>
</template>

<script>
import StockParcels from '~/components/stockParcels/StockParcels'
export default {
  components: {
    StockParcels
  },
  layout: 'default'
}
</script>
