import { Day } from './day';
import { Meal } from './meal';
import { NutritionalValues } from './nutritional-values';

export interface DailyCalories {
    id?: number,
    day?: Day,
    nutritionalValues?: NutritionalValues,
    meals?: Meal[]
}
