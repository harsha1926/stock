import { fetchSuppliers, postSupplier, putSupplier } from '~/api/suppliers'

const getSuppliers = context => {
  fetchSuppliers().then(response => {
    context.commit('SUPPLIERS_LIST_FETCHED', response.data)
  })
}

const addNewSupplier = (context, payload) => {
  return new Promise(function(resolve, reject) {
    postSupplier(payload)
      .then(response => {
        if (response.data) {
          context.commit('ADDED_SUPPLIER', response.data)
        }
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

const updateSupplier = (context, payload) => {
  return new Promise(function(resolve, reject) {
    putSupplier(payload)
      .then(response => {
        if (response.data) {
          context.commit('UPDATED_SUPPLIER', response.data)
        }
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

export default {
  getSuppliers,
  addNewSupplier,
  updateSupplier
}
