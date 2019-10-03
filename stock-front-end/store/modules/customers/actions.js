import { fetchCustomers, postCustomer, putCustomer } from '~/api/customers'

const getCustomers = context => {
  fetchCustomers().then(response => {
    context.commit('CUSTOMERS_LIST_FETCHED', response.data)
  })
}

const addNewcustomer = (context, payload) => {
  return new Promise(function(resolve, reject) {
    postCustomer(payload)
      .then(response => {
        if (response.data) {
          context.commit('ADDED_CUSTOMER', response.data)
        }
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

const updateCustomer = (context, payload) => {
  return new Promise(function(resolve, reject) {
    putCustomer(payload)
      .then(response => {
        if (response.data) {
          context.commit('UPDATED_CUSTOMER', response.data)
        }
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

export default {
  getCustomers,
  addNewcustomer,
  updateCustomer
}
