import axios from 'axios'

const getTransactions = () => {
  return axios.get(`/api/supplierFinancialTransaction`)
}

export { getTransactions }
