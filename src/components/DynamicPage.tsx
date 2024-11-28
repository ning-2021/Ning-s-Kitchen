import React from 'react';

interface DynamicPageProps {
    selectedTags: number[];
}

const DynamicPage: React.FC<DynamicPageProps> = (props) => {
    const {selectedTags} = props;
    return (
        <div>
            <h1>Dynamic Page Placeholder: {selectedTags.toString()}</h1>
        </div>
    );
};

export default DynamicPage;
