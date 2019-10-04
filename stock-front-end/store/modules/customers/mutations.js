const CUSTOMERS_LIST_FETCHED = (state, customers) => {
  state.customers = customers
}

const CUSTOMER_FETCHED = (state, customer) => {
  // TODO
  console.log(state, customer)
}

const ADDED_CUSTOMER = (state, customer) => {
  state.customers.push(customer)
}

const UPDATED_CUSTOMER = (state, updatedCustomer) => {
  let index = state.customers.findIndex(eachCustomer => {
    return eachCustomer.id == updatedCustomer.id
  })
  state.customers.splice(index, 1, updatedCustomer)
}

const DELETED_CUSTOMER = (state, id) => {
  let index = state.customers.findIndex(eachCustomer => {
    return eachCustomer.id == id
  })
  state.customers.splice(index, 1)
}
export default {
  CUSTOMERS_LIST_FETCHED,
  CUSTOMER_FETCHED,
  ADDED_CUSTOMER,
  UPDATED_CUSTOMER,
  DELETED_CUSTOMER
}
