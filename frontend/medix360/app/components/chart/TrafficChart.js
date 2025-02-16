"use client";
import { useEffect, useRef } from "react";
import * as echarts from "echarts";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";

const TrafficChart = () => {
  const chartRef = useRef(null);

  useEffect(() => {
    if (chartRef.current) {
      const chartInstance = echarts.init(chartRef.current);

      const options = {
        tooltip: { trigger: "item" },
        legend: { top: "5%", left: "center" },
        series: [
          {
            name: "Access From",
            type: "pie",
            radius: ["40%", "70%"],
            avoidLabelOverlap: false,
            label: { show: false, position: "center" },
            emphasis: {
              label: { show: true, fontSize: "18", fontWeight: "bold" },
            },
            labelLine: { show: false },
            data: [
              { value: 1048, name: "Search Engine" },
              { value: 735, name: "Direct" },
              { value: 580, name: "Email" },
              { value: 484, name: "Union Ads" },
              { value: 300, name: "Video Ads" },
            ],
          },
        ],
      };

      chartInstance.setOption(options);

      // Cleanup function to dispose the chart on unmount
      return () => {
        chartInstance.dispose();
      };
    }
  }, []);

  return (
    <div className="card">
      {/* Filter Dropdown */}
      <div className="filter">
        <a className="icon" href="#" data-bs-toggle="dropdown">
          <i className="bi bi-three-dots"></i>
        </a>
        <ul className="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
          <li className="dropdown-header text-start">
            <h6>Filter</h6>
          </li>
          <li><a className="dropdown-item" href="#">Today</a></li>
          <li><a className="dropdown-item" href="#">This Month</a></li>
          <li><a className="dropdown-item" href="#">This Year</a></li>
        </ul>
      </div>

      {/* Chart Content */}
      <div className="card-body pb-0">
        <h5 className="card-title">Website Traffic <span>| Today</span></h5>
        <div 
          ref={chartRef} 
          style={{
            minHeight: "400px", 
            width: "75%", // Decrease width to 80% of the parent
            margin: "0 auto" // Center the chart
          }} 
          className="echart"
        ></div>
      </div>
    </div>
  );
};

export default TrafficChart;
