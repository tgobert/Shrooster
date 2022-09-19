import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/services/product.service';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  products: {
    product: Product,
    quantity: number
  }[] = [];
  totalPrice!: number;
  cartProducts: Product[]=[];

  // quantity: number= 1;

  constructor(private productService: ProductService, private router: Router, private navBar: NavbarComponent) { }

  ngOnInit(): void {


    this.productService.getCart().subscribe(
      (cart) => {
        this.products = cart.products;

        this.products.forEach(
          (element) => {
            this.cartProducts.push(element.product);
          }
        );
        this.totalPrice = Math.round(cart.totalPrice);
      }
    );

  }


  // update quantity of product in cart based on user input

  productNumber() {
    const input = document.getElementById('productNumberID') as HTMLInputElement | null;
    // update total price of cart based on user input
    if (input) {
      this.totalPrice = 0;
      this.products.forEach(
        (element) => {
          this.totalPrice += Math.round(element.product.prodPrice * element.quantity);

        }
      );
      let cart = {
        cartCount: this.products.length,
        products: this.products,
        totalPrice: this.totalPrice
      };
      this.productService.setCart(cart);
    }

  }

  updateQuantity(product: Product, quantity: number): void {
    this.products.forEach(
      (element) => {
        if (element.product == product) {
          element.quantity = quantity;
        }
      }
    );
    this.totalPrice = 0;
    this.products.forEach(
      (element) => {
        this.totalPrice += Math.round(element.product.prodPrice * element.quantity);
      }
    );
    let cart = {
      cartCount: this.products.length,
      products: this.products,
      totalPrice: this.totalPrice
    };
    this.productService.setCart(cart);

  }

  emptyCart(): void {
    let cart = {
      cartCount: 0,
      products: [],
      totalPrice: 0.00
    };
    this.productService.setCart(cart);

    this.router.navigate(['/home']);
  }

  removeProduct(product: Product, quantity: number): void {
    this.products.forEach(
      (element, index) => {
        if (element.product.prodId === product.prodId) {
          this.products.splice(index, 1);
        }
      }
    );
    this.cartProducts.splice(this.cartProducts.indexOf(product), 1);
    this.totalPrice -= Math.round(product.prodPrice * quantity);
    let cart = {
      cartCount: this.products.length,
      products: this.products,
      totalPrice: this.totalPrice
    };
    this.productService.setCart(cart);

  }


}
