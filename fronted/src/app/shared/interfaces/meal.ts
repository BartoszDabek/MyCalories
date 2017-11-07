import { NutritionalValues } from './nutritional-values';
import { ProductMeal } from './product-meal';

export interface Meal {
    id: number,
    name: string,
    nutritionalValues: NutritionalValues,
    productMeals: ProductMeal[]
}
