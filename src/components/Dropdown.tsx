import React, { useState, useEffect } from 'react';
import { NavItem } from './types';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import { Recipe } from './types';

interface DropdownProps {
    navItem: NavItem;
    activeDropdown: number | null;
    selectedTags: number[];
    setSelectedTags: React.Dispatch<React.SetStateAction<number[]>>;
    toggleTagSelection: (id: number) => void
}

const Dropdown: React.FC<DropdownProps> = (props) => {
    const { navItem, activeDropdown, selectedTags, setSelectedTags, toggleTagSelection } = props;
    const navigate = useNavigate();
    const [effectTrigger, setEffectTrigger] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    return (
        <div>
            <Link to={navItem.href}>{navItem.name}</Link>
            {activeDropdown === navItem.id && navItem.dropdownItems && navItem.dropdownItems.length > 0 && (
                <ul className="dropdown">
                    {navItem.dropdownItems.map(dropdownItem => {
                        const isSelected = selectedTags.includes(dropdownItem.id);
                        return (
                            <li key={dropdownItem.id} onClick={() => toggleTagSelection(dropdownItem.id)}>
                                <input type="checkbox" checked={isSelected} onChange={() => {}}/>
                                <span>{dropdownItem.name}</span>
                            </li>
                        );
                    })}
                </ul>
            )}
        </div>);
};

export default Dropdown;