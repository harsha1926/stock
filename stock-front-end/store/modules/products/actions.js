import {
  fetchProducts,
  fetchSupplier,
  postProduct,
  putProduct,
  deleteProduct
} from '~/api/products'

const getProducts = context => {
  fetchProducts().then(response => {
    context.commit('PRODUCTS_LIST_FETCHED', response.data)
  })
}

const getProduct = (context, id) => {
  fetchProduct(id).then(response => {
    context.commit('PRODUCT_FETCHED', response.data)
  })
}

const createProduct = (context, payload) => {
  return new Promise(function(resolve, reject) {
    postProduct(payload)
      .then(response => {
        if (response.data) {
          context.commit('ADDED_PRODUCT', response.data)
        }
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

const updateProduct = (context, payload) => {
  return new Promise(function(resolve, reject) {
    putProduct(payload)
      .then(response => {
        if (response.data) {
          context.commit('UPDATED_PRODUCT', payload)
        }
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

const removeProduct = (context, id) => {
  return new Promise(function(resolve, reject) {
    deleteProduct(id)
      .then(response => {
        if (response.data) {
          context.commit('DELETED_PRODUCT', id)
        }
        resolve(response)
      })
      .catch(error => {
        reject(error)
      })
  })
}

export default {
  getProducts,
  getSupplier,
  createProduct,
  updateProduct,
  removeProduct
}
