const PRODUCTS_LIST_FETCHED = (state, products) => {
  state.products = products
}
const ADDED_PRODUCT = (state, products) => {
  state.products.push(products)
}
const UPDATED_PRODUCT = (state, products) => {
  state.products.push(products)
}

export default {
  PRODUCTS_LIST_FETCHED,
  ADDED_PRODUCT,
  UPDATED_PRODUCT
}
