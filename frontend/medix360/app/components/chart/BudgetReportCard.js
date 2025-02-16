import React, { useEffect } from "react";
import * as echarts from "echarts"; // Import echarts

const BudgetReportCard = () => {
  useEffect(() => {
    const budgetChart = echarts.init(document.getElementById("budgetChart"));

    budgetChart.setOption({
      legend: {
        data: ["Allocated Budget", "Actual Spending"],
      },
      radar: {
        indicator: [
          { name: "Sales", max: 6500 },
          { name: "Administration", max: 16000 },
          { name: "Information Technology", max: 30000 },
          { name: "Customer Support", max: 38000 },
          { name: "Development", max: 52000 },
          { name: "Marketing", max: 25000 },
        ],
      },
      series: [
        {
          name: "Budget vs spending",
          type: "radar",
          data: [
            {
              value: [4200, 3000, 20000, 35000, 50000, 18000],
              name: "Allocated Budget",
            },
            {
              value: [5000, 14000, 28000, 26000, 42000, 21000],
              name: "Actual Spending",
            },
          ],
        },
      ],
    });

    // Cleanup the chart when the component is unmounted
    return () => {
      budgetChart.dispose();
    };
  }, []); // Empty dependency array ensures this runs only once after the component is mounted

  return (
    <div className="card">
      <div className="filter">
        <a className="icon" href="#" data-bs-toggle="dropdown">
          <i className="bi bi-three-dots"></i>
        </a>
        <ul className="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
          <li className="dropdown-header text-start">
            <h6>Filter</h6>
          </li>
          <li>
            <a className="dropdown-item" href="#">
              Today
            </a>
          </li>
          <li>
            <a className="dropdown-item" href="#">
              This Month
            </a>
          </li>
          <li>
            <a className="dropdown-item" href="#">
              This Year
            </a>
          </li>
        </ul>
      </div>

      <div className="card-body pb-0">
        <h5 className="card-title">
          Budget Report <span>| This Month</span>
        </h5>

        <div
          id="budgetChart"

          style={{
            minHeight: "400px", 
            width: "65%", // Decrease width to 80% of the parent
            margin: "0 auto" // Center the chart
          }} 
          className="echart"
        ></div>
      </div>
    </div>
  );
};

export default BudgetReportCard;
