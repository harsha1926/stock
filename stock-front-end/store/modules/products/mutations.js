const PRODUCTS_LIST_FETCHED = (state, products) => {
  state.products = products
}

const PRODUCT_FETCHED = (state, product) => {
  // TODO
  console.log(state, product)
}

const ADDED_PRODUCT = (state, products) => {
  state.products.push(products)
}
const UPDATED_PRODUCT = (state, updatedProduct) => {
  let index = state.products.findIndex(eachProduct => {
    return eachProduct.id == updatedProduct.id
  })
  state.products.splice(index, 1, updatedProduct)
}

const DELETED_PRODUCT = (state, id) => {
  let index = state.products.findIndex(eachProduct => {
    return eachProduct.id == id
  })
  state.products.splice(index, 1)
}

export default {
  PRODUCTS_LIST_FETCHED,
  PRODUCT_FETCHED,
  ADDED_PRODUCT,
  UPDATED_PRODUCT,
  DELETED_PRODUCT
}
