import {OPEN_LOADER,CLOSE_LOADER} from './../actionTypes';

const initialState = {
    open:false,

}

 const loaderReducer = (state = initialState, actions) => {
    switch (actions.type) {
        case OPEN_LOADER:
            return {...state, open:true};
        case CLOSE_LOADER:
            return {...state, open:false};
        default:
            return {...state, open:false};
    }
}

export default loaderReducer;