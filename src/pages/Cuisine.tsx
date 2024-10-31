import React from 'react';

const Cuisine: React.FC = () => {
  const mealTypes = [
    { id: 1, title: 'Breakfast', description: 'Start your day right' },
    { id: 2, title: 'Lunch', description: 'Midday favorites' },
    { id: 3, title: 'Dinner', description: 'Evening meals' },
    { id: 4, title: 'Snacks', description: 'Quick bites' }
  ];

  return (
    <div className="page-container">
      <div className="content-wrapper">
        <h1 className="page-title">Meal Types</h1>
        <div className="items-grid">
          {mealTypes.map(item => (
            <div key={item.id} className="item-card">
              <div className="item-image-placeholder"></div>
              <h3>{item.title}</h3>
              <p>{item.description}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Cuisine;
