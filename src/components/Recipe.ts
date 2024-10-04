interface Recipe {
  id: number;
  title: string;
  description: string;
  instructions: string;
  rating: number;
  image: string;
  duration: number;
  created_at: string
}

export default Recipe;