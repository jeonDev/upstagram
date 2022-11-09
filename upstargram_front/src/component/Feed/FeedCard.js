const FeedCard = (feed) => {

    return (
        <div>
            <span>{feed.feed.feedNo}</span>
            <span>{feed.title}</span>
        </div>
    );
}

export default FeedCard;