const CUSTOMERS_LIST_FETCHED = (state, customers) => {
  state.customers = customers
}
const ADDED_CUSTOMER = (state, customer) => {
  state.customers.push(customer)
}
const UPDATED_CUSTOMER = (state, customer) => {
  state.customers.push(customer)
}

export default {
  CUSTOMERS_LIST_FETCHED,
  ADDED_CUSTOMER,
  UPDATED_CUSTOMER
}
