const SUPPLIERS_LIST_FETCHED = (state, suppliers) => {
  state.suppliers = suppliers
}

const SUPPLIER_FETCHED = (state, supplier) => {
  // TODO
  console.log(state, supplier)
}

const ADDED_SUPPLIER = (state, supplier) => {
  state.suppliers.push(supplier)
}

const UPDATED_SUPPLIER = (state, updatedSupplier) => {
  let index = state.suppliers.findIndex(eachSupplier => {
    return eachSupplier.id == updatedSupplier.id
  })
  state.suppliers.splice(index, 1, updatedSupplier)
}

const DELETED_SUPPLIER = (state, id) => {
  let index = state.suppliers.findIndex(eachSupplier => {
    return eachSupplier.id == id
  })
  state.suppliers.splice(index, 1)
}

export default {
  SUPPLIERS_LIST_FETCHED,
  SUPPLIER_FETCHED,
  ADDED_SUPPLIER,
  UPDATED_SUPPLIER,
  DELETED_SUPPLIER
}
