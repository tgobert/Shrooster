package com.revature.services;

import com.revature.dtos.TransactionDto;
import com.revature.dtos.TransactionQtyDTO;
import com.revature.models.OrderQuantityBought;
import com.revature.models.Product;
import com.revature.models.Transaction;
import com.revature.repositories.OrderQuantityRepo;
import com.revature.repositories.ProductRepository;
import com.revature.repositories.TransactionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private ProductRepository pr;
    private TransactionRepo tr;
    private OrderQuantityRepo OQBR;

    private ModelMapper maper=new ModelMapper();


    @Autowired
    public TransactionService(ProductRepository pr, TransactionRepo tr, OrderQuantityRepo OQBR) {
        this.pr = pr;
        this.tr = tr;
        this.OQBR = OQBR;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)

    public Transaction add(TransactionDto newTran){
        //stash list of products for later use
        List<TransactionQtyDTO> prodList=newTran.getOrderQuantityBoughts();
        newTran.setOrderQuantityBoughts(null);
        Transaction tempTran=maper.map(newTran,Transaction.class);

        tempTran.setTransactionId(0); //so the transaction create a new row and not update a row
        newTran.setOrderQuantityBoughts(null);


        //saves cathces the new transaction id and get a reference for the product  list

        Transaction dataBaseTran=tr.save(tempTran);

        Transaction TranReference= tr.getReferenceById(dataBaseTran.getTransactionId());

        //store product list
        for(TransactionQtyDTO p: prodList){
            Product tempproduct=pr.getReferenceById(p.getProductId());
            OrderQuantityBought qty=new OrderQuantityBought();
            qty.setProductId(tempproduct);
            qty.setTransactionId(TranReference);
            qty.setQuantity(p.getQty());
            OQBR.save(qty);
        }
        //pass the new record up
        return dataBaseTran;
    }

    public Optional<List<Transaction>> findByUserId(int id) {

      return tr.findTransactionByUserId(id);
    }






}
