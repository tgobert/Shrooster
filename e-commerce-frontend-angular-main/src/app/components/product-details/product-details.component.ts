import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/services/product.service';
import { WishHttpService } from 'src/app/services/wish-http.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  cartCount: number = 0;
  products: {
    product: Product,
    quantity: number
  }[] = [];
  subscription!: Subscription;
  totalPrice: number = 0;
  prodDetail: any;
  productId: string = "";


  wishCartCount!: number;
  wishProducts: {
    wishProduct: Product,
    wishQuantity: number
  }[] = [];
  wishSubscription!: Subscription;
  wishTotalPrice!: number;
  newWishList: Product[] = [];
  prodIdList: number[] = [];
  addId: number = 0;

  errorMessage: string = "";


  @Input() productInfo!: Product;

  constructor(private productsService: ProductService, private wishService: WishHttpService, private param: ActivatedRoute) { }

  ngOnInit(): void {
    this.subscription = this.productsService.getCart().subscribe(
      (cart) => {
        this.cartCount = cart.cartCount;
        this.products = cart.products;
        this.totalPrice = cart.totalPrice;
      }
    );
    this.productId = this.param.snapshot.params['id'];
    this.getProduct(parseInt(this.productId));
  }

   getProduct(id: number) {
    this.productsService.getSingleProduct(id).subscribe({
      next: (response) => {
        console.log("PRODUCT RESPONSE ******");

        this.prodDetail = response;
      },
      error: (err) => {
        console.log(err);
      },
      complete() {
        console.log("Success Got Product")
      },
    })

  }


  addToCart(product: Product): void {
    // check if product is already in cart and if product quantity is less than 4 if so,
    // increase quantity by 1 and update total price
    let productInCart = this.products.find((element) => {
      return element.product.prodId === product.prodId;
    });

    if (productInCart) {
      if (productInCart.quantity < 4 && product.prodQuantity > 0 && product.prodQuantity > productInCart.quantity) {

        productInCart.quantity++;
        this.totalPrice += Math.round(product.prodPrice);
        this.cartCount++;

      }
      // if product quantity is 4 or more, display error message
      else if (productInCart.quantity >= 4) {
        this.errorMessage = "You can only buy 4 of this product at a time";
      }
    } // if product is not in cart, add it to cart and update total price
    // if prod.quantity is less than 0 display error message
    else {
      if (product.prodQuantity > 0) {

        this.products.push({ product: product, quantity: 1 });
        this.totalPrice += Math.round(product.prodPrice);
        this.cartCount++;

      }
      else {
        this.errorMessage = "Sorry, this product is out of stock";
      }
    }
    this.productsService.setCart({
      cartCount: this.cartCount,
      products: this.products,
      totalPrice: this.totalPrice
      });

  }

  // Add product to wishcart
  addToWishCart(product: Product): void {
    this.wishService.getwishListByUserId().subscribe(
      (wishCart) => {
        this.newWishList = wishCart;
        console.log(wishCart);
      }
    );
    if (!this.newWishList.includes(product)) {
      this.wishService.addWish(product.prodId).subscribe(
        {
          next: (response) => {
            console.log(response + "response");
          },
          error: (error) => {
            console.log(error);
            console.log("Item is already in your wish list.");
          },
          complete: () => {
            console.log("Request complete");
          }
        }
      );
      return;
    };

}
}
