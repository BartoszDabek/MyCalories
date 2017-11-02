import { NutritionalValues } from './nutritional-values';
import { CategoryInterface } from './category';

export interface ProductInterface {
    id: number,
    name: string,
    nutritionalValues: NutritionalValues,
    category: CategoryInterface
}
