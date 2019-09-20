import axios from 'axios'

const getSupplierTransactions = () => {
  return axios.get(`/api/supplierTransaction`)
}

export { getSupplierTransactions }
