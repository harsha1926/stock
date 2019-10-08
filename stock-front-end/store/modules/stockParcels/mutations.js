const STOCKPARCELS_LIST_FETCHED = (state, stockParcels) => {
  state.stockParcels = stockParcels
}

const STOCKPARCEL_FETCHED = (state, stockParcel) => {
  // TODO
  console.log(state, stockParcel)
}

const ADDED_STOCKPARCEL = (state, stockParcel) => {
  state.stockParcels.push(stockParcel)
}

const UPDATED_STOCKPARCEL = (state, updatedStockParcel) => {
  let index = state.stockParcels.findIndex(eachStockParcel => {
    return eachStockParcel.id == updatedStockParcel.id
  })
  state.stockParcels.splice(index, 1, updatedStockParcel)
}

const DELETED_STOCKPARCEL = (state, id) => {
  let index = state.stockParcels.findIndex(eachStockParcel => {
    return eachStockParcel.id == id
  })
  state.stockParcels.splice(index, 1)
}

export default {
  STOCKPARCELS_LIST_FETCHED,
  STOCKPARCEL_FETCHED,
  ADDED_STOCKPARCEL,
  UPDATED_STOCKPARCEL,
  DELETED_STOCKPARCEL
}
