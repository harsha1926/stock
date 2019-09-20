package stock.mysql;

import com.google.inject.AbstractModule;

public class StockMySqlModule extends AbstractModule {
    @Override
    public void configure() {
        bind(ProductsRepository.class).to(ProductsRepositoryImpl.class).asEagerSingleton();
        bind(SupplierRepository.class).to(SupplierRepositoryImpl.class).asEagerSingleton();
        bind(CustomerRepository.class).to(CustomerRepositoryImpl.class).asEagerSingleton();
        bind(FinancialTransactionRepository.class).to(FinancialTransactionRepositoryImpl.class).asEagerSingleton();
        bind(StockRepository.class).to(StockRepositoryImpl.class).asEagerSingleton();
        bind(SupplierTransactionRepository.class).to(SupplierTransactionRepositoryImpl.class).asEagerSingleton();
        bind(FinancialTransactionDetailsRepository.class).to(FinancialTransactionDetailsRepositoryImpl.class).asEagerSingleton();
        bind(SuppliersFinancialTransactionRepository.class).to(SuppliersFinancialTransactionRepositoryImpl.class).asEagerSingleton();

    }
}
