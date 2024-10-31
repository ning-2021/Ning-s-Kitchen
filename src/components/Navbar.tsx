import React, { useState } from 'react';
import { Link } from 'react-router-dom';

interface NavItem {
    label: string;
    href: string
}

const Navbar: React.FC = () => {
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const navItems: NavItem[] = [
        {label: 'Home', href: '/'},  // Add this line
        {label: 'Meal Type', href: '/meal-type'},
        {label: 'Food Categories', href: '/food-categories'},
        {label: 'Cooking Methods', href: '/cooking-methods'},
        {label: 'Flavor', href: '/flavor'},
        {label: 'Cuisine', href: '/cuisines'},
        {label: 'Dietary Restriction', href: '/dietary-restriction'},
        {label: 'Skill Level', href: '/skill-level'}
    ];

    return (
        <nav className="navbar">
            <ul className={`navbar-links ${isOpen ? 'active' : ''}`}>
                {navItems.map((item, index) => (
                    <li key={index}>
                        <Link to={item.href}>{item.label}</Link>
                    </li>
                ))}
            </ul>
        </nav>
    );
};

export default Navbar;

