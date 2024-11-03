export interface Recipe {
  id: number;
  title: string;
  description: string;
  instructions: string;
  rating: number;
  image: string;
  duration: number;
  created_at: string
}

export interface DropdownItem {
    id: number;
    label: string;
    href: string
}

export interface NavItem {
    id: number;
    name: string;
    href: string;
    dropdownItems?: DropdownItem[]
}