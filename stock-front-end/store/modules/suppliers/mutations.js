const SUPPLIERS_LIST_FETCHED = (state, suppliers) => {
  state.suppliers = suppliers
}
const ADDED_SUPPLIER = (state, supplier) => {
  state.suppliers.push(supplier)
}
const UPDATED_SUPPLIER = (state, supplier) => {
  state.suppliers.push(supplier)
}

export default {
  SUPPLIERS_LIST_FETCHED,
  ADDED_SUPPLIER,
  UPDATED_SUPPLIER
}
