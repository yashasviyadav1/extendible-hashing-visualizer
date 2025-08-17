
// finding the X least significant bits from a number, using mask approach
// similar thing used in backend as well
export function get_X_LBS_from_number(num, x){
    let mask = (1 << x) - 1;
    return (num & mask).toString(2).padStart(x, '0');
}

