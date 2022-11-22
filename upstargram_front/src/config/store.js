import { createStore } from "redux";

const userInfo = {
    id : '',
    name : '',
    sex : '',
    tel : ''
}

const rootReducer = (state = userInfo, action) => {
    switch (action.type) {
        case "SET_USER" :
            return {
                ...state,
                id:action.id,
                name:action.name,
                sex:action.sex,
                tel:action.tel
            };
        default:
            return state;
    }
};

const store = createStore(rootReducer);

export default store;