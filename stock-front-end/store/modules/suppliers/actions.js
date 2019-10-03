import {
  fetchSuppliers,
  fetchSupplier,
  postSupplier,
  putSupplier,
  deleteSupplier
} from '~/api/suppliers'

const getSuppliers = context => {
  fetchSuppliers().then(response => {
    context.commit('SUPPLIERS_LIST_FETCHED', response.data)
  })
}

const getSupplier = (context, id) => {
  fetchSupplier(id).then(response => {
    context.commit('SUPPLIER_FETCHED', response.data)
  })
}

const createSupplier = (context, payload) => {
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
          context.commit('UPDATED_SUPPLIER', payload)
        }
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

const removeSupplier = (context, id) => {
  return new Promise(function(resolve, reject) {
    deleteSupplier(id)
      .then(response => {
        if (response.data) {
          context.commit('DELETED_SUPPLIER', id)
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
  getSupplier,
  createSupplier,
  updateSupplier,
  removeSupplier
}
