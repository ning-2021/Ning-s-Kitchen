import React, { useEffect, useState } from 'react';
import axios from 'axios';
import RecipeList from './RecipeList';
import Recipe from './Recipe';


const RecipeMain: React.FC = () => {
  const [allRecipes, setAllRecipes] = useState<Recipe[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchRecipes = async () => {
      try {
        const response = await axios.get<Recipe[]>('http://192.168.86.17:8080/recipes');
        setAllRecipes(response.data);
      } catch (err) {
        setError((err as Error).message);
      } finally {
        setLoading(false);
      }
    };
    fetchRecipes();
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  const newestRecipes = [...allRecipes].sort((a, b) => new Date(b.created_at).getTime() - new Date(a.created_at).getTime()).slice(0, 5);

  return (
    <div className="recipe-main">
      <RecipeList title="Full Recipes" recipes={allRecipes} />
    </div>
  );
};

export default RecipeMain;
