import axios from 'axios'

const getTransactionDetails = () => {
  return axios.get(`/api/financialTransactionDetails`)
}

export { getTransactionDetails }
