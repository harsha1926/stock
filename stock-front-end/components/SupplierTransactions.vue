<template>
  <v-container fluid>
    <v-card>
      <v-card-title>
        <v-layout row justify-left>
          <v-dialog v-model="dialog" persistent max-width="600px">
            <template v-slot:activator="{ on }">
              <v-btn color="primary" icon dark v-on="on">
                <v-icon>add</v-icon>
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="headline">
                  {{
                  $t(
                  'app.actions.supplier_transactions.supplier_transaction_create_title'
                  )
                  }}
                </span>
              </v-card-title>
              <v-card-text>
                <v-container grid-list-md>
                  <v-layout wrap>
                    <v-flex xs12 md6>
                      <v-text-field
                        :label="
                          $t('app.actions.supplier_transactions.supplier_label')
                        "
                        required
                      ></v-text-field>
                    </v-flex>
                    <v-flex xs12 md6>
                      <v-text-field
                        :label="
                          $t('app.actions.supplier_transactions.product_label')
                        "
                      ></v-text-field>
                    </v-flex>
                    <v-flex xs12 md6>
                      <v-text-field
                        :label="
                          $t('app.actions.supplier_transactions.quantity_label')
                        "
                        required
                      ></v-text-field>
                    </v-flex>
                    <v-flex xs12 md6>
                      <v-text-field
                        :label="
                          $t('app.actions.supplier_transactions.amount_label')
                        "
                        required
                      ></v-text-field>
                    </v-flex>
                    <v-flex xs12 md6>
                      <v-select
                        :items="['Cash', 'Credit', 'Bank Transfer', 'Cheque']"
                        :label="
                          $t(
                            'app.actions.supplier_transactions.transfer_type_label'
                          )
                        "
                        required
                      ></v-select>
                    </v-flex>
                    <v-flex xs12 md6>
                      <v-text-field
                        :label="
                          $t(
                            'app.actions.supplier_transactions.bill_number_label'
                          )
                        "
                        required
                      ></v-text-field>
                    </v-flex>
                  </v-layout>
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" flat @click="dialog = false">Close</v-btn>
                <v-btn color="blue darken-1" flat @click="dialog = false">Save</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-layout>

        <v-spacer></v-spacer>
        <v-text-field v-model="search" append-icon="search" label="Search" hide-details></v-text-field>
      </v-card-title>
      <v-card-text>
        <v-data-table
          :headers="headers"
          :items="products"
          :items-per-page="5"
          :search="search"
          class="elevation-1"
        >
          <template slot="items" slot-scope="props">
            <tr>
              <td>{{ props.item.name }}</td>
              <td>{{ props.item.billNumber }}</td>
              <td>{{ props.item.purchasedOn }}</td>
              <td>{{ props.item.amount }}</td>
              <td>{{ props.item.amountPaid }}</td>
              <td>{{ props.item.paidOn }}</td>
              <td>{{ props.item.amount - props.item.amountPaid }}</td>
            </tr>
          </template>
        </v-data-table>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      headers: [
        {
          text: 'Supplier',
          align: 'left',
          sortable: false,
          value: 'name'
        },
        { text: 'Bill Number', value: 'billNumber' },
        { text: 'Purchased On', value: 'purchasedOn' },
        { text: 'Total Amount', value: 'amount' },
        { text: 'Amount Paid', value: 'amountPaid' },
        { text: 'Paid On', value: 'paidOn' },
        { text: 'Amount Due', value: 'amountDue' }
      ],
      search: null,
      dialog: false,

      products: [
        {
          name: 'Neelima',
          billNumber: '1234',
          purchasedOn: '10 July 2019',
          amount: 30000,
          amountPaid: 2000,
          paidOn: '11 July 2019'
        },
        {
          name: 'Neelima',
          billNumber: '1234',
          purchasedOn: '10 July 2019',
          amount: 30000,
          amountPaid: 2000,
          paidOn: '11 July 2019'
        },
        {
          name: 'Harsha',
          billNumber: '3456',
          purchasedOn: '20 July 2019',
          amount: 3000,
          amountPaid: 2000,
          paidOn: '05 August 2019'
        },
        {
          name: 'Naresh',
          billNumber: '5678',
          purchasedOn: '30 July 2018',
          amount: 1500,
          amountPaid: 1500,
          paidOn: '14 December 2018'
        }
      ]
    }
  }
}
</script>
