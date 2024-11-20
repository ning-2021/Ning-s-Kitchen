import React, { useState } from 'react';
import { NavItem } from './types';
import { Link } from 'react-router-dom';

interface DropdownProps {
    navItem: NavItem;
    activeDropdown: number | null;
    selectedTags: number[];
    setSelectedTags: React.Dispatch<React.SetStateAction<number[]>>;
}

const Dropdown: React.FC<DropdownProps> = (props) => {
    const { navItem, activeDropdown, selectedTags, setSelectedTags } = props;

    const toggleOption = (id: number): void => {
        setSelectedTags(prevSelected =>
            prevSelected.includes(id)
                ? prevSelected.filter(item => item !== id)
                : [...prevSelected, id]
        );
    };

    return (
        <div>
            <Link to={navItem.href}>{navItem.name}</Link>
            {activeDropdown === navItem.id && navItem.dropdownItems && navItem.dropdownItems.length > 0 && (
                <ul className="dropdown">
                    {navItem.dropdownItems.map(dropdownItem => {
                        const isSelected = selectedTags.includes(dropdownItem.id);
                        return (
                            <li key={dropdownItem.id} onClick={() => toggleOption(dropdownItem.id)}>
                                <input type="checkbox" checked={isSelected} />
                                <span>{dropdownItem.name}</span>
                            </li>
                        );
                    })}
                </ul>
            )}
        </div>);
}

export default Dropdown;