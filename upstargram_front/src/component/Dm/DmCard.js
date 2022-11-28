import { Card } from "react-bootstrap";
import { useSelector } from "react-redux";

const DmCard = (props) => {
    const {dm} = props;
    const id = useSelector((state) => state.id);

    return (
        <div className={dm.sendId === id ? 'my-text-div' : 'friend-text-div'}>
            <div className={dm.sendId === id ? 'my-text-container' : 'friend-text-div'}>
                <div className={dm.sendId === id ? 'my-text' : 'friend-text'}>{ dm.message }</div>
            </div>
        </div>
    )
}

export default DmCard;