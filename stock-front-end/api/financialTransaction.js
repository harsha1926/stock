import axios from 'axios'

const getTransactions = () => {
  return axios.get(`/api/financialTransactions`)
}

export { getTransactions }
