import {
  fetchCustomers,
  fetchCustomer,
  postCustomer,
  putCustomer,
  deleteCustomer
} from '~/api/customers'

const getCustomers = context => {
  fetchCustomers().then(response => {
    context.commit('CUSTOMERS_LIST_FETCHED', response.data)
  })
}

const getCustomer = (context, id) => {
  fetchCustomer(id).then(response => {
    context.commit('CUSTOMER_FETCHED', response.data)
  })
}

const createCustomer = (context, payload) => {
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
          context.commit('UPDATED_CUSTOMER', payload)
        }
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

const removeCustomer = (context, id) => {
  return new Promise(function(resolve, reject) {
    deleteCustomer(id)
      .then(response => {
        if (response.data) {
          context.commit('DELETED_CUSTOMER', id)
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
  getCustomer,
  createCustomer,
  updateCustomer,
  removeCustomer
}
